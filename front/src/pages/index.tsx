import { LNB } from '@/components/LNB';
import { Inter } from 'next/font/google';
const inter = Inter({ subsets: ['latin'] });

export default function Home() {
  return (
    <div>
      <LNB />
    </div>
  );
}
