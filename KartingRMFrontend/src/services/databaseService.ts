import axios from 'axios'
import type {
  FeeTypeEntity,
  FrequencyDiscountEntity,
  PeopleDiscountEntity,
  HolidayEntity,
  BirthdayEntity,
  RentEntity,
  ReceiptEntity
} from '../types/entities'

// Fee Types
export const getAllFeeTypes = async (): Promise<FeeTypeEntity[]> => {
  const res = await axios.get('/feeType/all')
  return res.data
}

// Frequency Discounts
export const getAllFrequencyDiscounts = async (): Promise<FrequencyDiscountEntity[]> => {
  const res = await axios.get('/frequencyDiscount/all')
  return res.data
}

// People Discounts
export const getAllPeopleDiscounts = async (): Promise<PeopleDiscountEntity[]> => {
  const res = await axios.get('/peopleDiscount/all')
  return res.data
}

// Special Day Fees
export const getAllHolidays = async (): Promise<HolidayEntity[]> => {
  const res = await axios.get('/specialDay/holiday/all')
  return res.data
}
export const getAllBirthdays = async (): Promise<BirthdayEntity[]> => {
  const res = await axios.get('/specialDay/birthday/all')
  return res.data
}

// Rents
export const getAllRents = async (): Promise<RentEntity[]> => {
  const res = await axios.get('/rent/all')
  return res.data
}

// Receipts
export const getAllReceipts = async (): Promise<ReceiptEntity[]> => {
  const res = await axios.get('/receipt/all')
  return res.data
}