import React, { useEffect, useState } from 'react'
import { getAllFrequencyDiscounts } from '../../services/databaseService'
import type { FrequencyDiscountEntity } from '../../types/entities'
import { useNavigate } from 'react-router-dom'

const FrequencyDiscount: React.FC = () => {
  const [discounts, setDiscounts] = useState<FrequencyDiscountEntity[]>([])
  const navigate = useNavigate()

  useEffect(() => {
    getAllFrequencyDiscounts().then(setDiscounts)
  }, [])

  return (
    <div>
      <h2>Descuentos por Frecuencia</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Descripción</th>
            <th>Descuento</th>
            <th>Frecuencia Mínima</th>
          </tr>
        </thead>
        <tbody>
          {discounts.map(d => (
            <tr key={d.id}>
              <td>{d.id}</td>
              <td>{d.description}</td>
              <td>{d.discount}</td>
              <td>{d.minFrequency}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
    </div>
  )
}

export default FrequencyDiscount