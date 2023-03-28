import s from '../../styles/map/C_LoaderTest.module.css';

import { Ring } from '@uiball/loaders';

export const Loader = () => {
  return (
    <div className={s.loaderContainer}>
      <Ring size={40} lineWeight={5} speed={2} color='black' />
    </div>
  );
};
