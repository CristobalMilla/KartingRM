import React from 'react'
import { useNavigate } from 'react-router-dom'

const MainMenu: React.FC = () => {
  const navigate = useNavigate()

  return (
    <div style={{ padding: 32 }}>
      <h1>KartingRM</h1>
      <h2>Menu Principal</h2>
      <button onClick={() => navigate('/create-rent')}>Crear Renta</button>
      <button onClick={() => navigate('/calendar')}>Ver Calendario</button>
      <button onClick={() => navigate('/reports/fee-type')}>Informe segun Tipo de Tarifa</button>
      <button onClick={() => navigate('/reports/people-discount')}>Informe segun Descuento por Persona</button>
      <h2>Vistas de Base de Datos</h2>
      <button onClick={() => navigate('/database/fee-type')}>Tipos de Tarifas</button>
      <button onClick={() => navigate('/database/frequency-discount')}>Descuentos por Frecuencia</button>
      <button onClick={() => navigate('/database/people-discount')}>Descuentos por Persona</button>
      <button onClick={() => navigate('/database/special-day-fee')}>Tarifas por Día Especial</button>
      <button onClick={() => navigate('/database/rent-receipt')}>Rentas y Recibos</button>
    </div>
  )
}

export default MainMenu