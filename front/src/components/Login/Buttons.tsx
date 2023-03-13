import React from 'react';

interface ButtonProps {
  text: string;
  onClick?: () => void;
  className?: string;
}

const Btn = ({ text, onClick, className }: ButtonProps) => {
  return (
    <button className={className} onClick={onClick}>
      {text}
    </button>
  );
};

export default Btn;
