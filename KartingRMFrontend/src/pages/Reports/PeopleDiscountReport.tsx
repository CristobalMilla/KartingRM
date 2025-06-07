import React, { useState } from 'react'
import { getPeopleDiscountReport } from '../../services/reportService'
import ReportTable from '../../components/ReportTable'
import { useNavigate } from 'react-router-dom'

const PeopleDiscountReport: React.FC = () => {
  const [startMonth, setStartMonth] = useState('')
  const [endMonth, setEndMonth] = useState('')
  const [data, setData] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const fetchReport = async () => {
    setLoading(true)
    try {
      const report = await getPeopleDiscountReport(startMonth, endMonth)
      setData(report)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ padding: 32 }}>
      <h2>Informe por Descuento de Personas</h2>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
      <div>
        <label>Mes inicio (YYYY-MM): <input type="month" value={startMonth} onChange={e => setStartMonth(e.target.value)} /></label>
        <label style={{ marginLeft: 16 }}>Mes fin (YYYY-MM): <input type="month" value={endMonth} onChange={e => setEndMonth(e.target.value)} /></label>
        <button onClick={fetchReport} disabled={!startMonth || !endMonth || loading} style={{ marginLeft: 16 }}>
          {loading ? 'Cargando...' : 'Ver Informe'}
        </button>
      </div>
      <div style={{ marginTop: 24 }}>
        <ReportTable data={data} />
      </div>
    </div>
  )
}

export default PeopleDiscountReport