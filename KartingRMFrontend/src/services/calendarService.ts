import axios from 'axios'
import type { CalendarEvent } from '../types/entities'

// Returns a map: { "Week of YYYY-MM-DD": CalendarEvent[] }
export const getCalendarEventsForWeeks = async (
  startDate: string,
  numberOfWeeks: number
): Promise<Record<string, CalendarEvent[]>> => {
  const res = await axios.get('/calendar/getEventsFromWeek', {
    params: { startDate, numberOfWeeks }
  })
  return res.data
}