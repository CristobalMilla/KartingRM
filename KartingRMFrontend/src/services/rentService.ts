import axios from 'axios'
import type { FeeTypeEntity, CalendarEvent, RentRequestDTO, RentEntity } from '../types/entities.ts'

const API_BASE = '' // Basandose en el archivo gateway-service.yaml en el repostirio

export const getFeeTypes = async (): Promise<FeeTypeEntity[]> => {
  const res = await axios.get(`${API_BASE}/feeType/all`)
  return res.data
}

export const getCalendarEvents = async (weekStartDate: string): Promise<CalendarEvent[]> => {
  const res = await axios.get(`${API_BASE}/calendar/singleWeek`, {
    params: { weekStartDate }
  })
  return res.data
}

export const createRent = async (rentRequest: RentRequestDTO): Promise<RentEntity> => {
  const res = await axios.post(`${API_BASE}/rent/complete`, rentRequest)
  return res.data
}

export const getAvailableTimeSlots = async (rentDate: string, rentDuration: number): Promise<string[]> => {
  const res = await axios.get(`/rent/getAvailableTimeSlots`, {
    params: { rentDate, rentDuration }
  })
  return res.data // List of LocalTime as strings, e.g., ["10:00:00", "12:00:00"]
}