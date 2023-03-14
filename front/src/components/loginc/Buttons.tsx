import React from 'react';

interface ButtonProps {
  text: string;
  onClick?: () => void;
  className?: string;
  type?: string;
}

const Btn = ({ text, onClick, className, type }: ButtonProps) => {
  return (
    <button className={className} onClick={onClick}>
      {text}
    </button>
  );
};

export default Btn;
