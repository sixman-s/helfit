import React from 'react';
import { Input } from 'semantic-ui-react';

interface InputProps {
  placeholder: string;
  label: string;
}

const Tag: React.FC<InputProps> = ({ placeholder, label }) => (
  <Input
    icon='tags'
    iconPosition='left'
    labelPosition='right'
    placeholder={placeholder}
    className='tagicon'
    label={{ tag: true, content: label, color: 'blue' }}
  />
);

export default Tag;
