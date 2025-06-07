import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import MainMenu from './pages/MainMenu'
import CreateRent from './pages/CreateRent'
import Calendar from './pages/Calendar'
import ReportFeeTypeReport from './pages/Reports/FeeTypeReport'
import PeopleDiscountReport from './pages/Reports/PeopleDiscountReport'
import FeeType from './pages/Database/FeeType'
import FrequencyDiscount from './pages/Database/FrequencyDiscount'
import PeopleDiscount from './pages/Database/PeopleDiscount'
import SpecialDayFee from './pages/Database/SpecialDayFee'
import RentReceipt from './pages/Database/RentReceipt'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainMenu />} />
        <Route path="/create-rent" element={<CreateRent />} />
        <Route path="/calendar" element={<Calendar />} />
        <Route path="/reports/fee-type" element={<ReportFeeTypeReport />} />
        <Route path="/reports/people-discount" element={<PeopleDiscountReport />} />
        <Route path="/database/fee-type" element={<FeeType />} />
        <Route path="/database/frequency-discount" element={<FrequencyDiscount />} />
        <Route path="/database/people-discount" element={<PeopleDiscount />} />
        <Route path="/database/special-day-fee" element={<SpecialDayFee />} />
        <Route path="/database/rent-receipt" element={<RentReceipt />} />
      </Routes>
    </Router>
  )
}

export default App
