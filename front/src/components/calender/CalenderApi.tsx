import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const CalenderApi = ({ setDate, date, open, setOpen }) => {
  return (
    <DatePicker
      inline
      autoFocus
      selected={date}
      maxDate={new Date()}
      onChange={(date) => {
        setDate(date);
        setOpen(true);
      }}
      onKeyDown={() => setOpen(!open)}
      dateFormatCalendar='MMMM'
      showYearDropdown
    />
  );
};
export default CalenderApi;
