// url: http://localhost:3000/oauth2/receive?access_token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMDAyNDY4NTgwMjYxOTI2NTM1OTkiLCJyb2xlIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjc5NjcyMzYyfQ.9x6MEY0Z7DO2TvwBv8SRl0wuLkVWDnegyiKENdlFqiE
// access_token만 짤라서 로컬스토리지에 저장하고
// 홈으로 이동시킨다.
const OAuth2 = () => {
  if (typeof window !== 'undefined') {
    // Get the URL from the address bar
    const url: string = window.location.href;

    // Use the URLSearchParams API to extract the access_token
    const urlSearchParams: URLSearchParams = new URLSearchParams(
      url.split('?')[1]
    );
    const accessToken: string | null = urlSearchParams.get('access_token');

    // Save the access_token to local storage
    if (accessToken) {
      localStorage.setItem('accessToken', accessToken);
      window.location.replace('/');
    }
  }
};
export default OAuth2;
