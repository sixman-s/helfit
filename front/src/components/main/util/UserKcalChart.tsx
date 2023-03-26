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

const AxisLeft = ({ scale, width, goal, setTooltip }) => {
  const ref = useRef<SVGGElement>(null);
  const Line = 200 - 0.04 * goal;
  useEffect(() => {
    if (ref.current) {
      select(ref.current)
        .call(axisLeft(scale).ticks(6))
        .append('g')
        .attr('opacity', '1')
        .attr('transform', `translate(0,${Line})`)
        .append('line')
        .attr('stroke', '#b8c3d9')
        .attr('x2', `${width}`)
        .attr('stroke-width', '2')
        .attr('stroke-dasharray', '5');
    }
  }, [scale]);

  return (
    <g
      ref={ref}
      className={styled.line}
      onMouseEnter={(e) => setTooltip({ x: e.clientX, y: e.clientY, goal })}
      onMouseLeave={(e) => setTooltip(null)}
    />
  );
};

const ToolTipPop = ({ data }) => {
  if (data !== null) {
    return data.goal ? (
      <article
        className={styled.popup_container}
        style={{ top: data.y, left: data.x }}
      >
        <p className={styled.text}>
          목표 칼로리: {data.goal} <span>kcal</span>
        </p>
      </article>
    ) : (
      <article
        className={styled.popup_container}
        style={{ top: data.y, left: data.x }}
      >
        <p className={styled.date}>등록일: {data.recodedAt}</p>
        <p className={styled.text}>
          당일 칼로리: {data.kcal} <span>kcal</span>
        </p>
      </article>
    );
  } else null;
};

const ChartBars = ({ data, height, scaleX, scaleY, setTooltip }) => {
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
            onMouseEnter={(e) =>
              setTooltip({ x: e.clientX, y: e.clientY, kcal, recodedAt })
            }
            onMouseLeave={(e) => setTooltip(null)}
          />
        );
      })}
    </>
  );
};

const UserKcalChart = ({ data, goal }) => {
  const [tooltip, setTooltip] = useState(null);
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
  const scaleY = scaleLinear().domain([5000, 0]).range([0, height]);

  return (
    <>
      <svg
        ref={ref}
        width={width + margin.left + margin.right}
        height={height + margin.top + margin.bottom}
      >
        <g transform={`translate(${margin.left}, ${margin.top})`}>
          <AxisBottom scale={scaleX} transform={`translate(0, ${height})`} />
          <AxisLeft
            scale={scaleY}
            width={width}
            goal={goal}
            setTooltip={setTooltip}
          />

          <ChartBars
            data={kcalData}
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
export default UserKcalChart;
