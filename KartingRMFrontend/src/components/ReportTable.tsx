import React from 'react'

interface ReportTableProps {
  data: any[]
}

const ReportTable: React.FC<ReportTableProps> = ({ data }) => {
  if (!data || data.length === 0) return <div>No hay datos para mostrar.</div>
  const columns = Object.keys(data[0])

  return (
    <table>
      <thead>
        <tr>
          {columns.map(col => (
            <th key={col}>{col}</th>
          ))}
        </tr>
      </thead>
      <tbody>
        {data.map((row, idx) => (
          <tr key={idx}>
            {columns.map(col => (
              <td key={col}>{row[col]?.toString()}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  )
}

export default ReportTable