import React, { useState } from 'react'
import { getCalendarEventsForWeeks } from '../services/calendarService'
import type { CalendarEvent } from '../types/entities'
import { useNavigate } from 'react-router-dom'
import { Calendar as BigCalendar, dateFnsLocalizer, Views } from 'react-big-calendar'
import { format, parse, startOfWeek, getDay } from 'date-fns'
import 'react-big-calendar/lib/css/react-big-calendar.css'

const locales = {
  'es': require('date-fns/locale/es')
}
const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek: () => startOfWeek(new Date(), { weekStartsOn: 1 }),
  getDay,
  locales,
})

const Calendar: React.FC = () => {
  const [startDate, setStartDate] = useState('')
  const [events, setEvents] = useState<any[]>([])
  const [loading, setLoading] = useState(false)
  const navigate = useNavigate()

  const fetchEvents = async () => {
    setLoading(true)
    try {
      // Only fetch 1 week for visual calendar
      const data = await getCalendarEventsForWeeks(startDate, 1)
      // Flatten events for the week
      const weekEvents = Object.values(data).flat() as CalendarEvent[]
      // Map to react-big-calendar format
      setEvents(weekEvents.map(ev => ({
        title: ev.title,
        start: new Date(ev.start),
        end: new Date(ev.end),
        resource: ev.client_name
      })))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div style={{ padding: 32 }}>
      <h2>Calendario de Rentas (Semana)</h2>
      <button onClick={() => navigate('/')}>Volver al Menú</button>
      <div>
        <label>Fecha de inicio: <input type="date" value={startDate} onChange={e => setStartDate(e.target.value)} /></label>
        <button onClick={fetchEvents} disabled={!startDate || loading} style={{ marginLeft: 16 }}>
          {loading ? 'Cargando...' : 'Ver Semana'}
        </button>
      </div>
      <div style={{ height: 600, marginTop: 24 }}>
        <BigCalendar
          localizer={localizer}
          events={events}
          defaultView={Views.WEEK}
          views={['week']}
          step={30}
          timeslots={2}
          startAccessor="start"
          endAccessor="end"
          titleAccessor="title"
          tooltipAccessor="resource"
          messages={{
            week: 'Semana',
            day: 'Día',
            today: 'Hoy',
            previous: 'Anterior',
            next: 'Siguiente',
            noEventsInRange: 'No hay eventos en este rango.'
          }}
        />
      </div>
    </div>
  )
}

export default Calendar