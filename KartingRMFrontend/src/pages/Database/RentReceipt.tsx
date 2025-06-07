import React, { useEffect, useState } from 'react'
import { getAllRents, getAllReceipts } from '../../services/databaseService'
import type { RentEntity, ReceiptEntity } from '../../types/entities'
import { useNavigate } from 'react-router-dom'

const RentReceipt: React.FC = () => {
  const [rents, setRents] = useState<RentEntity[]>([])
  const [receipts, setReceipts] = useState<ReceiptEntity[]>([])
  const navigate = useNavigate()

  useEffect(() => {
    getAllRents().then(setRents)
    getAllReceipts().then(setReceipts)
  }, [])

  return (
    <div>
      <h2>Rentas</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Código</th>
            <th>Fecha</th>
            <th>Hora</th>
            <th>Tipo de Tarifa</th>
            <th>N° Personas</th>
            <th>Cliente Principal</th>
            <th>Total</th>
          </tr>
        </thead>
        <tbody>
          {rents.map(r => (
            <tr key={r.rent_id}>
              <td>{r.rent_id}</td>
              <td>{r.rent_code}</td>
              <td>{r.rentDate}</td>
              <td>{r.rent_time}</td>
              <td>{r.fee_type_id}</td>
              <td>{r.people_number}</td>
              <td>{r.mainClient}</td>
              <td>{r.total_price}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <h2>Recibos</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>ID Renta</th>
            <th>Subcliente</th>
            <th>Tarifa Base</th>
            <th>Descuento Tamaño</th>
            <th>Descuento Especial</th>
            <th>Precio Agregado</th>
            <th>IVA</th>
            <th>Precio Final</th>
          </tr>
        </thead>
        <tbody>
          {receipts.map(r => (
            <tr key={r.receipt_id}>
              <td>{r.receipt_id}</td>
              <td>{r.rentId}</td>
              <td>{r.sub_client}</td>
              <td>{r.base_tariff}</td>
              <td>{r.size_discount}</td>
              <td>{r.special_discount}</td>
              <td>{r.aggregated_price}</td>
              <td>{r.iva_price}</td>
              <td>{r.final_price}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
    </div>
  )
}

export default RentReceipt