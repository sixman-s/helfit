import d3, { axisBottom, axisLeft, scaleBand, scaleLinear, select } from 'd3';
import { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import {
  BarChartProps,
  AxisBottomProps,
  AxisLeftProps,
  BarsProps
} from './types';

const AxisBottom = ({ scale, transform }: AxisBottomProps) => {
  const ref = useRef<SVGGElement>(null);

  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisBottom(scale));
    }
  }, [scale]);

  return <g ref={ref} transform={transform} color='#a3b1cc' />;
};

const AxisLeft = ({ scale }: AxisLeftProps) => {
  const ref = useRef<SVGGElement>(null);

  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisLeft(scale));
    }
  }, [scale]);

  return <g ref={ref} color='#a3b1cc' />;
};

const ChartBars = ({ data, height, scaleX, scaleY }: BarsProps) => {
  return (
    <>
      {data.map(({ modifiedAt, weight }) => (
        <rect
          key={`bar-${modifiedAt}`}
          x={scaleX(modifiedAt)}
          y={scaleY(weight)}
          width={scaleX.bandwidth()}
          height={height - scaleY(weight)}
          fill='#3361ff'
        />
      ))}
    </>
  );
};

const WeightChart = ({ token, userId }: BarChartProps) => {
  const [weight, setweight] = useState([]);
  const DateLengthFour = (date: string) => {
    return date.slice(5, date.length).replaceAll('-', '.');
  };
  const weightData = weight.map((data) =>
    data.modifiedAt.length > 8
      ? { ...data, modifiedAt: DateLengthFour(data.modifiedAt) }
      : data
  );

  useEffect(() => {
    if (token) {
      const headers = {
        headers: {
          Authorization: `Bearer ${token}`
        }
      };
      const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/stat/physical`;
      axios
        .get(url, headers)
        .then((res) => {
          console.log(res.data.body.data);
          setweight(res.data.body.data);
        })
        .catch((error) => console.log(error));
    }
  }, []);

  const margin = { top: 10, right: 0, bottom: 20, left: 40 };
  const width = 330 - margin.left - margin.right;
  const height = 240 - margin.top - margin.bottom;

  const scaleX = scaleBand()
    .domain(
      weightData.map(({ modifiedAt }) => (modifiedAt ? modifiedAt : null))
    )
    .range([0, width])
    .padding(0.4);
  const scaleY = scaleLinear()
    .domain([
      0,
      Math.max(
        ...weightData
          .map(({ weight }) => weight)
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
          data={weightData}
          height={height}
          scaleX={scaleX}
          scaleY={scaleY}
        />
      </g>
    </svg>
  );
};

export default WeightChart;
