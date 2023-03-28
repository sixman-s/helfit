import LNB from './LNB';
import ChatGPT from './main/util/ChatGPT';

type mainrops = {
  children: React.ReactNode;
};

const Layout = ({ children }: mainrops) => {
  return (
    <main>
      <LNB />
      <ChatGPT />
      <section>{children}</section>
    </main>
  );
};

export default Layout;
