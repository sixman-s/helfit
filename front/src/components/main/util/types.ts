import { ScaleBand, ScaleLinear } from 'd3';

export interface Data {
  recodedAt: string;
  kcal: number;
}

export interface BarChartProps {
  token: string;
  userId: string;
}

export interface AxisBottomProps {
  scale: ScaleBand<string>;
  transform: string;
}

export interface AxisLeftProps {
  scale: ScaleLinear<number, number, never>;
  width: number;
  line: number;
}

export interface BarsProps {
  data: any;
  height: number;
  scaleX: AxisBottomProps['scale'];
  scaleY: AxisLeftProps['scale'];
}

export type LineChartDataType = {
  label?: string;
  value: number;
  dateTime: string;
  borderColor?: string;
  borderWidth?: number;
  backgroundColor?: string;
};
/* eslint-disable-next-line */
export interface LineChartProps {
  data: LineChartDataType[]; // data is collection of array
  size: number[]; // size represents here width and height of the bar chart
  paddingInner?: number; // its consider to distance between bars
  strokeWidth?: number; //
}
