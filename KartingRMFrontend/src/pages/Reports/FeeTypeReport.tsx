import React, { useState } from 'react'
import { getFeeTypeReport } from '../../services/reportService'
import ReportTable from '../../components/ReportTable'
import { useNavigate } from 'react-router-dom'

const FeeTypeReport: React.FC = () => {
  const [startMonth, setStartMonth] = useState('')
  const [endMonth, setEndMonth] = useState('')
  const [data, setData] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const fetchReport = async () => {
    setLoading(true)
    try {
      const report = await getFeeTypeReport(startMonth, endMonth)
      setData(Array.isArray(report) ? report : [])
    } catch (err) {
      setData([])
      // Optionally: alert('No se pudo cargar el informe.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ padding: 32 }}>
      <h2>Informe por Tipo de Tarifa</h2>
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

export default FeeTypeReport