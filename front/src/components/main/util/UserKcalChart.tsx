import React, { useEffect, useRef, useState } from 'react';
import * as d3 from 'd3';
import styled from '../../../styles/main/C_chart.module.css';
import {
  axisBottom,
  axisLeft,
  axisRight,
  scaleBand,
  scaleLinear,
  select
} from 'd3';
const AxisBottom = ({ scale, transform }) => {
  const ref = useRef<SVGGElement>(null);

  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisBottom(scale));
    }
  }, [scale]);

  return <g ref={ref} transform={transform} className={styled.line} />;
};

const AxisLeft = ({ scale, width, goal }) => {
  const ref = useRef<SVGGElement>(null);
  const Line = ~~((5000 - goal) / 20);
  console.log(Line);
  useEffect(() => {
    if (ref.current) {
      select(ref.current)
        .call(axisLeft(scale).ticks(6))
        .append('g')
        .attr('opacity', '1')
        .attr('transform', `translate(0,${Line})`)
        .append('line')
        .attr('stroke', '#a3b1cc')
        .attr('x2', `${width}`)
        .attr('stroke-dasharray', '5');
    }
  }, [scale]);

  return <g ref={ref} className={styled.line} />;
};

const ChartBars = ({
  data,
  height,
  scaleX,
  scaleY,
  onMouseEnter,
  onMouseLeave
}) => {
  return (
    <>
      {data.map(({ recodedAt, kcal }) => {
        return (
          <rect
            key={`bar-${recodedAt}`}
            x={scaleX(recodedAt)}
            y={scaleY(kcal)}
            width={scaleX.bandwidth()}
            height={height - scaleY(kcal)}
            fill='#3361ff'
          />
        );
      })}
    </>
  );
};

const UserKcalChart = ({ data, goal }) => {
  const [tooltip, setTooltip] = useState(null);
  const kcalArr = data.map(({ kcal }) => kcal);
  const recodedAtArr = data.map(({ recodedAt }) => recodedAt);
  const ref = useRef(null);
  const DateLengthFour = (date: string) => {
    return date.slice(5, date.length).replaceAll('-', '.');
  };
  const kcalData = data.map((data) =>
    data.recodedAt.length > 8
      ? { ...data, recodedAt: DateLengthFour(data.recodedAt) }
      : data
  );
  const margin = { top: 20, right: 40, bottom: 20, left: 40 };
  const width = 330 - margin.left - margin.right;
  const height = 240 - margin.top - margin.bottom;
  const scaleX = scaleBand()
    .domain(kcalData.map(({ recodedAt }) => (recodedAt ? recodedAt : null)))
    .range([0, width])
    .padding(0.4);
  const scaleY = scaleLinear().domain([0, 5000]).range([height, 0]);

  return (
    <svg
      ref={ref}
      width={width + margin.left + margin.right}
      height={height + margin.top + margin.bottom}
    >
      <g transform={`translate(${margin.left}, ${margin.top})`}>
        <AxisBottom scale={scaleX} transform={`translate(0, ${height})`} />
        <AxisLeft scale={scaleY} width={width} goal={goal} />

        <ChartBars
          data={kcalData}
          height={height}
          scaleX={scaleX}
          scaleY={scaleY}
          onMouseEnter={(event) => {
            setTooltip({});
          }}
          onMouseLeave={() => setTooltip(null)}
        />
      </g>
    </svg>
  );
};
export default UserKcalChart;
