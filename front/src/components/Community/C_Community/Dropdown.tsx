import { FunctionComponent } from 'react';
import { Dropdown, DropdownProps } from 'semantic-ui-react';

export interface Option {
  key: string;
  text: string;
  value: string;
}

interface DropdownPropsWithOption extends DropdownProps {
  options: Option[];
}

const DropdownWithOption: FunctionComponent<DropdownPropsWithOption> = ({
  options,
  ...restProps
}) => (
  <Dropdown
    placeholder='갤러리'
    search
    selection
    options={options}
    {...restProps}
    className='DropDown'
  />
);

export default DropdownWithOption;
