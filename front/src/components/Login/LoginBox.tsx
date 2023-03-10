import boxstyle from '../../styles/Login/C_LoginBox.module.css';
import { useState } from 'react';
import { useRouter } from 'next/router';

const LoginBox = () => {
  const [userID, setUserID] = useState('');
  const [password, setPassword] = useState('');
  const router = useRouter();

  const handleUserIDChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUserID(event.target.value);
  };

  const handlePasswordChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // TODO: 로그인 로직 구현
    if (userID === 'user' && password === 'password') {
      router.push('/dashboard');
    } else {
      alert('아이디 또는 비밀번호가 일치하지 않습니다.');
    }
  };

  return (
    <>
      <div className={boxstyle.box}>
        <form onSubmit={handleSubmit}>
          <h2>아이디</h2>
          <input
            type='text'
            name='userID'
            value={userID}
            onChange={handleUserIDChange}
            className={boxstyle.login__form}
          />
          <h2>비밀번호</h2>
          <input
            type='password'
            name='password'
            value={password}
            onChange={handlePasswordChange}
          />
          <button type='submit'>로그인</button>
        </form>
      </div>
    </>
  );
};
export default LoginBox;
