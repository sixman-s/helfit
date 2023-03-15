import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

type Props = {
  setDate: React.Dispatch<React.SetStateAction<Date>>;
  date: Date;
  open: boolean;
  setOpen: React.Dispatch<React.SetStateAction<boolean>>;
};
const CalendarApi = ({ setDate, date, open, setOpen }: Props) => {
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
export default CalendarApi;
