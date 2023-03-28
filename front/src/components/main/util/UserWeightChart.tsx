import React, { useEffect, useRef, useState } from 'react';
import styled from '../../../styles/main/C_chart.module.css';
import { axisBottom, axisLeft, scaleBand, scaleLinear, select } from 'd3';

const AxisBottom = ({ scale, transform }) => {
  const ref = useRef<SVGGElement>(null);

  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisBottom(scale));
    }
  }, [scale]);

  return <g ref={ref} transform={transform} className={styled.line} />;
};

const AxisLeft = ({ scale }) => {
  const ref = useRef<SVGGElement>(null);
  useEffect(() => {
    if (ref.current) {
      select(ref.current).call(axisLeft(scale).ticks(6));
    }
  }, [scale]);

  return <g ref={ref} className={styled.line} />;
};

const ToolTipPop = ({ data }) => {
  if (data !== null) {
    return (
      <article
        className={styled.popup_container}
        style={{ top: data.y, left: data.x }}
      >
        <p className={styled.date}>등록일: {data.lastModifiedAt}</p>
        <p className={styled.text}>
          당일 몸무게: {data.weight} <span>weight</span>
        </p>
      </article>
    );
  }
};

const ChartBars = ({ data, height, scaleX, scaleY, setTooltip }) => {
  return (
    <>
      {data.map(({ lastModifiedAt, weight }) => {
        return (
          <rect
            key={`bar-${lastModifiedAt}`}
            x={scaleX(lastModifiedAt)}
            y={scaleY(weight)}
            width={scaleX.bandwidth()}
            height={height - scaleY(weight)}
            fill='#EF770A'
            onMouseEnter={(e) =>
              setTooltip({ x: e.clientX, y: e.clientY, weight, lastModifiedAt })
            }
            onMouseLeave={(e) => setTooltip(null)}
          />
        );
      })}
    </>
  );
};

const UserWeightChart = ({ data }) => {
  const [tooltip, setTooltip] = useState(null);
  const ref = useRef(null);
  const DateLengthFour = (date: string) => {
    return date.slice(5, date.length).replaceAll('-', '.');
  };
  const weightData = data.map((data) =>
    data.lastModifiedAt.length > 8
      ? { ...data, lastModifiedAt: DateLengthFour(data.lastModifiedAt) }
      : data
  );
  const margin = { top: 20, right: 40, bottom: 20, left: 40 };
  const width = 330 - margin.left - margin.right;
  const height = 240 - margin.top - margin.bottom;
  const scaleX = scaleBand()
    .domain(
      weightData.map(({ lastModifiedAt }) =>
        lastModifiedAt ? lastModifiedAt : null
      )
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
    <>
      <svg
        ref={ref}
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
            setTooltip={setTooltip}
          />
        </g>
      </svg>
      <ToolTipPop data={tooltip} />
    </>
  );
};

export default UserWeightChart;
