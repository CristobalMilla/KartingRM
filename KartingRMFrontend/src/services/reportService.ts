import axios from 'axios'

export const getFeeTypeReport = async (startMonth: string, endMonth: string): Promise<any[]> => {
  const res = await axios.get('/report/fee-type', { params: { startMonth, endMonth } })
  return res.data
}

export const getPeopleDiscountReport = async (startMonth: string, endMonth: string): Promise<any[]> => {
  const res = await axios.get('/report/people-discount', { params: { startMonth, endMonth } })
  return res.data
}