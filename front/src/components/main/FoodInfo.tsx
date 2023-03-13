import layout from '../../styles/main/C_infoLayout.module.css';

const FoodInfo = () => {
  const username = '홍길동';
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>Today's Food</h2>
      </header>
    </article>
  );
};

export default FoodInfo;
