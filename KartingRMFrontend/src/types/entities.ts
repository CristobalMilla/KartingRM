// FeeTypeEntity
export interface FeeTypeEntity {
  fee_type_id: number;
  lap_number: number;
  max_time: number;
  price: string; // BigDecimal as string
  duration: number;
}

// RentEntity
export interface RentEntity {
  rent_id: number;
  rent_code: string;
  rentDate: string; // LocalDate as ISO string (e.g., "2025-05-23")
  rent_time: string; // LocalTime as string (e.g., "10:30:00")
  fee_type_id: number;
  people_number: number;
  mainClient: string;
  total_price: string; // BigDecimal as string
}

// ReceiptEntity
export interface ReceiptEntity {
  receipt_id: number;
  rentId: number;
  sub_client: string;
  base_tariff: string;
  size_discount: string;
  special_discount: string;
  aggregated_price: string;
  iva_price: string;
  final_price: string;
}

// CalendarEvent
export interface CalendarEvent {
  title: string;
  start: string; // Date as ISO string
  end: string;   // Date as ISO string
  client_name: string;
}

// RentRequestDTO
export interface RentRequestDTO {
  rent: RentEntity;
  subClients: string[];
}

// FrequencyDiscountEntity
export interface FrequencyDiscountEntity {
  frequency_discount_id: number
  category: string
  min_frequency: number
  max_frequency: number
  discount: string // BigDecimal as string
  
}

// PeopleDiscountEntity
export interface PeopleDiscountEntity {
  people_discount_id: number
  min_people: number
  max_people: number
  discount: string // BigDecimal as string
}

// HolidayEntity
export interface HolidayEntity {
  holiday_id: number
  name: string
  date: string // ISO date string
  discount: string // BigDecimal as string
}

// BirthdayEntity
export interface BirthdayEntity {
  birthday_id: number
  name: string
  date: string // ISO date string
  discount: string // BigDecimal as string
}