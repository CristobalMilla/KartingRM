import React, { useEffect, useState } from 'react'
import { getAllHolidays, getAllBirthdays } from '../../services/databaseService'
import type { HolidayEntity, BirthdayEntity } from '../../types/entities'
import { useNavigate } from 'react-router-dom'

const SpecialDayFee: React.FC = () => {
  const [holidays, setHolidays] = useState<HolidayEntity[]>([])
  const [birthdays, setBirthdays] = useState<BirthdayEntity[]>([])
  const navigate = useNavigate()

  useEffect(() => {
    getAllHolidays().then(setHolidays)
    getAllBirthdays().then(setBirthdays)
  }, [])

  return (
    <div>
      <h2>Días Especiales - Feriados</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Fecha</th>
            <th>Tarifa Especial</th>
          </tr>
        </thead>
        <tbody>
          {holidays.map(h => (
            <tr key={h.id}>
              <td>{h.id}</td>
              <td>{h.name}</td>
              <td>{h.date}</td>
              <td>{h.specialFee}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <h2>Días Especiales - Cumpleaños</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre Cliente</th>
            <th>Fecha</th>
            <th>Tarifa Especial</th>
          </tr>
        </thead>
        <tbody>
          {birthdays.map(b => (
            <tr key={b.id}>
              <td>{b.id}</td>
              <td>{b.clientName}</td>
              <td>{b.date}</td>
              <td>{b.specialFee}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
    </div>
  )
}

export default SpecialDayFee