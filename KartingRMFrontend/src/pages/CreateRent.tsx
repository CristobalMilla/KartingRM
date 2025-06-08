import React, { useState, useEffect } from 'react'
import { getFeeTypes, getAvailableTimeSlots, createRent } from '../services/rentService'
import type { FeeTypeEntity, RentEntity, RentRequestDTO } from '../types/entities.ts'
import { useNavigate } from 'react-router-dom'

const MAX_PEOPLE = 15

const CreateRent: React.FC = () => {
  const [step, setStep] = useState(1)
  const [feeTypes, setFeeTypes] = useState<FeeTypeEntity[]>([])
  const [selectedDate, setSelectedDate] = useState('')
  const [selectedFeeType, setSelectedFeeType] = useState<FeeTypeEntity | null>(null)
  const [mainClient, setMainClient] = useState('')
  const [peopleNumber, setPeopleNumber] = useState(1)
  const [subClients, setSubClients] = useState<string[]>([])
  const [rentCode, setRentCode] = useState('')
  const [availableSlots, setAvailableSlots] = useState<string[]>([])
  const [selectedSlot, setSelectedSlot] = useState('')
  const navigate = useNavigate()

  // Step 1: Fetch fee types
  useEffect(() => {
    getFeeTypes().then(setFeeTypes)
  }, [])

  // Step 2: Fetch available slots when date or fee type changes
  useEffect(() => {
    if (selectedDate && selectedFeeType) {
      getAvailableTimeSlots(selectedDate, selectedFeeType.duration).then(setAvailableSlots)
    }
  }, [selectedDate, selectedFeeType])

  // Step 3: Generate rent code
  useEffect(() => {
    if (mainClient && selectedDate) {
      setRentCode(`${mainClient}_${selectedDate}`)
    }
  }, [mainClient, selectedDate])

  // Step navigation
  const nextStep = () => setStep(s => s + 1)
  const prevStep = () => setStep(s => s - 1)

  // Step 4: Handle subclients input
  const handleSubClientChange = (idx: number, value: string) => {
    const updated = [...subClients]
    updated[idx] = value
    setSubClients(updated)
  }

  // Step 5: Submit rent
  const handleSubmit = async () => {
    if (!selectedFeeType || !selectedSlot) return
    const rent: RentEntity = {
      rent_id: 0,
      rent_code: rentCode,
      rentDate: selectedDate,
      rent_time: selectedSlot,
      fee_type_id: selectedFeeType.fee_type_id,
      people_number: peopleNumber,
      mainClient,
      total_price: '0'
    }
    const rentRequest: RentRequestDTO = { rent, subClients }
    await createRent(rentRequest)
    alert('Renta creada exitosamente')
    // Optionally redirect or reset form
  }

  // Render steps
  return (
    <div style={{ padding: 32 }}>
      <h2>Crear Renta</h2>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
      {step === 1 && (
        <div>
          <label>Fecha: <input type="date" value={selectedDate} onChange={e => setSelectedDate(e.target.value)} /></label>
          <br />
          <label>Tipo de tarifa:
            <select value={selectedFeeType?.fee_type_id || ''} onChange={e => {
              const ft = feeTypes.find(f => f.fee_type_id === Number(e.target.value))
              setSelectedFeeType(ft || null)
            }}>
              <option value="">Seleccione...</option>
              {feeTypes.map(ft => (
                <option key={ft.fee_type_id} value={ft.fee_type_id}>
                  {ft.lap_number} vueltas / {ft.duration} min - ${ft.price}
                </option>
              ))}
            </select>
          </label>
          <br />
          <button disabled={!selectedDate || !selectedFeeType} onClick={nextStep}>Siguiente</button>
        </div>
      )}
      {step === 2 && (
        <div>
          <h3>Horarios disponibles para {selectedDate}</h3>
          <select value={selectedSlot} onChange={e => setSelectedSlot(e.target.value)}>
            <option value="">Seleccione un horario...</option>
            {availableSlots.map(slot => (
              <option key={slot} value={slot}>{slot}</option>
            ))}
          </select>
          <br />
          <button onClick={prevStep}>Anterior</button>
          <button disabled={!selectedSlot} onClick={nextStep}>Siguiente</button>
        </div>
      )}
      {step === 3 && (
        <div>
          <label>Cliente principal: <input value={mainClient} onChange={e => setMainClient(e.target.value)} /></label>
          <br />
          <label>Número de personas:
            <input type="number" min={1} max={MAX_PEOPLE} value={peopleNumber} onChange={e => setPeopleNumber(Number(e.target.value))} />
          </label>
          <br />
          <div>Código de renta: {rentCode}</div>
          <button onClick={prevStep}>Anterior</button>
          <button disabled={!mainClient || peopleNumber < 1} onClick={() => {
            setSubClients(Array(peopleNumber).fill(''))
            nextStep()
          }}>Siguiente</button>
        </div>
      )}
      {step === 4 && (
        <div>
          <h3>Subclientes</h3>
          {subClients.map((name, idx) => (
            <div key={idx}>
              <label>Subcliente {idx + 1}: <input value={name} onChange={e => handleSubClientChange(idx, e.target.value)} /></label>
            </div>
          ))}
          <button onClick={prevStep}>Anterior</button>
          <button disabled={subClients.some(name => !name)} onClick={nextStep}>Siguiente</button>
        </div>
      )}
      {step === 5 && (
        <div>
          <h3>Confirmar y crear renta</h3>
          <div>Fecha: {selectedDate}</div>
          <div>Tipo de tarifa: {selectedFeeType?.lap_number} vueltas / {selectedFeeType?.duration} min</div>
          <div>Cliente principal: {mainClient}</div>
          <div>Número de personas: {peopleNumber}</div>
          <div>Subclientes: {subClients.join(', ')}</div>
          <button onClick={prevStep}>Anterior</button>
          <button onClick={handleSubmit}>Crear Renta</button>
        </div>
      )}
    </div>
  )
}

export default CreateRent