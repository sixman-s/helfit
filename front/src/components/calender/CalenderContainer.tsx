import styled from '../../styles/calender/C_calenderContainer.module.css';
import CalenderApi from '@/components/calender/CalenderApi';
import ReaderPop from './CalenderPop';
import { useState } from 'react';

const CalenderContainer = () => {
  const [open, setOpen] = useState(false);
  const [startDate, setStartDate] = useState(new Date());
  //aa
  return (
    <article className={styled.article}>
      <header className={styled.header}>
        <h1 className={styled.h1}>Calender</h1>
      </header>
      <figure className={styled.figure}>
        <CalenderApi
          setDate={setStartDate}
          date={startDate}
          open={open}
          setOpen={setOpen}
        />
        <ReaderPop date={startDate} open={open} setOpen={setOpen} />
      </figure>
    </article>
  );
};

export default CalenderContainer;
