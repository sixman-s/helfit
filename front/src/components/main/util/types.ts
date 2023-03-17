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
}

export interface BarsProps {
  data: any;
  height: number;
  scaleX: AxisBottomProps['scale'];
  scaleY: AxisLeftProps['scale'];
}
