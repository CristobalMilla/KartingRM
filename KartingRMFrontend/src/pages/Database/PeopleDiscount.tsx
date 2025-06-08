import React, { useEffect, useState } from 'react'
import { getAllPeopleDiscounts } from '../../services/databaseService'
import type { PeopleDiscountEntity } from '../../types/entities'
import { useNavigate } from 'react-router-dom'

const PeopleDiscount: React.FC = () => {
  const [discounts, setDiscounts] = useState<PeopleDiscountEntity[]>([])
  const navigate = useNavigate()

  useEffect(() => {
    getAllPeopleDiscounts().then(setDiscounts)
  }, [])

  return (
    <div>
      <h2>Descuentos por Persona</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Descuento</th>
            <th>Mínimo de Personas</th>
            <th>Máximo de Personas</th>
          </tr>
        </thead>
        <tbody>
          {discounts.map(d => (
            <tr key={d.people_discount_id}>
              <td>{d.people_discount_id}</td>
              <td>{d.discount}</td>
              <td>{d.min_people}</td>
              <td>{d.max_people}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
    </div>
  )
}

export default PeopleDiscount