import React, { useEffect, useState } from 'react'
import { getAllFeeTypes } from '../../services/databaseService'
import type { FeeTypeEntity } from '../../types/entities'
import { useNavigate } from 'react-router-dom'

const FeeType: React.FC = () => {
  const [feeTypes, setFeeTypes] = useState<FeeTypeEntity[]>([])
  const navigate = useNavigate()

  useEffect(() => {
    getAllFeeTypes().then(setFeeTypes)
  }, [])

  return (
    <div>
      <h2>Tipos de Tarifas</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Vueltas</th>
            <th>Tiempo Máx</th>
            <th>Precio</th>
            <th>Duración</th>
          </tr>
        </thead>
        <tbody>
          {feeTypes.map(ft => (
            <tr key={ft.fee_type_id}>
              <td>{ft.fee_type_id}</td>
              <td>{ft.lap_number}</td>
              <td>{ft.max_time}</td>
              <td>{ft.price}</td>
              <td>{ft.duration}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
    </div>
  )
}

export default FeeType