import styled from '../../styles/calendar/C_calendarContainer.module.css';
import CalendarApi from './CalendarApi';
import ReaderPop from './CalendarPop';
import { useState } from 'react';

const CalendarContainer = () => {
  const [open, setOpen] = useState(false);
  const [startDate, setStartDate] = useState(new Date());
  return (
    <div className={styled.article}>
      <article className={styled.article}>
        <header className={styled.header}>
          <h1 className={styled.h1}>Calendar</h1>
        </header>
        <figure className={styled.figure}>
          <CalendarApi
            setDate={setStartDate}
            date={startDate}
            open={open}
            setOpen={setOpen}
          />
          <ReaderPop date={startDate} open={open} setOpen={setOpen} />
        </figure>
      </article>
    </div>
  );
};

export default CalendarContainer;
