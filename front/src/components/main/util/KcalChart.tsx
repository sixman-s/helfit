import { axisBottom, axisLeft, scaleBand, scaleLinear, select, path } from 'd3';
import { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import {
  BarChartProps,
  AxisBottomProps,
  AxisLeftProps,
  BarsProps
} from './types';
import styled from '../../../styles/main/C_chart.module.css';

const AxisBottom = ({ scale, transform }: AxisBottomProps) => {
  const ref = useRef<SVGGElement>(null);

  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisBottom(scale));
    }
  }, [scale]);

  return <g ref={ref} transform={transform} className={styled.line} />;
};

const AxisLeft = ({ scale }: AxisLeftProps) => {
  const ref = useRef<SVGGElement>(null);

  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisLeft(scale));
    }
  }, [scale]);

  return <g ref={ref} className={styled.line} />;
};

const ChartBars = ({ data, height, scaleX, scaleY }: BarsProps) => {
  return (
    <>
      {data.map(({ recodedAt, kcal }) => (
        <rect
          key={`bar-${recodedAt}`}
          x={scaleX(recodedAt)}
          y={scaleY(kcal)}
          width={scaleX.bandwidth()}
          height={height - scaleY(kcal)}
          fill='#3361ff'
        />
      ))}
    </>
  );
};

const KcalChart = ({ token, userId }: BarChartProps) => {
  const [kcal, setKcal] = useState([]);

  const DateLengthFour = (date: string) => {
    return date.slice(5, date.length).replaceAll('-', '.');
  };

  const kcalData = kcal.map((data) =>
    data.recodedAt.length > 8
      ? { ...data, recodedAt: DateLengthFour(data.recodedAt) }
      : data
  );

  useEffect(() => {
    if (token) {
      const headers = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/stat/calendar/${userId}`;
      axios
        .get(url, headers)
        .then((res) => {
          setKcal(res.data.body.data);
        })
        .catch((error) => console.log(error));
    }
  }, []);

  const margin = { top: 10, right: 0, bottom: 20, left: 40 };
  const width = 330 - margin.left - margin.right;
  const height = 240 - margin.top - margin.bottom;

  const scaleX = scaleBand()
    .domain(kcalData.map(({ recodedAt }) => (recodedAt ? recodedAt : null)))
    .range([0, width])
    .padding(0.4);

  const scaleY = scaleLinear()
    .domain([
      0,
      Math.max(
        ...kcalData
          .map(({ kcal }) => kcal)
          .sort(function (a, b) {
            return a - b;
          })
      )
    ])
    .range([height, 0]);

  return (
    <svg
      width={width + margin.left + margin.right}
      height={height + margin.top + margin.bottom}
    >
      <g transform={`translate(${margin.left}, ${margin.top})`}>
        <AxisBottom scale={scaleX} transform={`translate(0, ${height})`} />
        <AxisLeft scale={scaleY} />
        <ChartBars
          data={kcalData}
          height={height}
          scaleX={scaleX}
          scaleY={scaleY}
        />
      </g>
    </svg>
  );
};

export default KcalChart;
