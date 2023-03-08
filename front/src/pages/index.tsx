import Head from 'next/head';
import Image from 'next/image';
import { Inter } from 'next/font/google';
import styled from 'styled-components';

const inter = Inter({ subsets: ['latin'] });

export default function Home() {
  return <Practics>helfit index.tsx</Practics>;
}

const Practics = styled.div`
  color: ${({ theme }) => theme.texts.text_1};
`;
