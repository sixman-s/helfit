interface Post {
  id: number;
  title: string;
  nickName: string;
  date: string;
  tag: string;
  views: string;
  content: string; // content 변수 추가
}

const examplePosts: Post[] = [
  {
    id: 1,
    title: '안녕하세요 첫 번째 글을 남기게 되었습니다.   ',
    nickName: '김세훈',
    date: '23.01.01',
    tag: 'Health',
    views: '1202',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 2,
    title: '가슴운동 루틴 공유점',
    nickName: '김준희',
    date: '23.01.01',
    tag: 'Health',
    views: '565',
    content:
      'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'
  },
  {
    id: 3,
    title: '오운완 페이지는 어디에 있나요?    ',
    nickName: '윤영원',
    date: '23.01.01',
    tag: 'Health',
    views: '402',
    content:
      'Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.'
  },
  {
    id: 4,
    title: '홍대헬스장 가려면 어디로 가야 해요??    ',
    nickName: '개똥이',
    date: '23.01.01',
    tag: 'Health',
    views: '182',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 5,
    title: '뉴진스의 Hype Boy요 ',
    nickName: 'C.Bumstead',
    date: '23.01.01',
    tag: 'Health',
    views: '444',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 6,
    title: '커쟈아아아아아    ',
    nickName: '김태형',
    date: '23.01.01',
    tag: 'Health',
    views: '2',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 7,
    title: '메인프로젝트 화이팅',
    nickName: '김지열',
    date: '23.01.01',
    tag: 'Health',
    views: '12',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 8,
    title: '멘트가 생각이 안나네요   ',
    nickName: '현지원',
    date: '23.01.01',
    tag: 'Health',
    views: '102',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 9,
    title: '아무거나 적어봤습니다. ',
    nickName: '윤성빈',
    date: '23.01.01',
    tag: 'Health',
    views: '122',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  },
  {
    id: 10,
    title: '운동을 가야되는데 알배겨서 가기가 싫음',
    nickName: '윤석열',
    date: '23.01.01',
    tag: 'Health',
    views: '1202',
    content:
      'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.'
  }
];
export default examplePosts;
