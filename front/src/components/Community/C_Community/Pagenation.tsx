import React from 'react';
import { Pagination } from 'semantic-ui-react';

const Pagenation = () => (
  <Pagination
    defaultActivePage={1}
    firstItem={null}
    lastItem={null}
    pointing
    secondary
    totalPages={7}
  />
);

export default Pagenation;
