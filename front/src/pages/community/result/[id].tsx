import style from '../../../styles/Community/P_Result.module.css';
import Layout from '@/components/MainLayout';
import Searchbar from '@/components/Community/SearchBar';

import { Pagination, PaginationProps } from 'semantic-ui-react';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';

interface Post {
  boardId: number;
  categoryId: number;

  boardImageUrl: string | null;
  createdAt: string;
  modifiedAt: string;
  tags: { tagId: number; tagName: string }[];
  text: string;
  title: string;
  userNickname: string;
  view: number;
}
const URL = process.env.NEXT_PUBLIC_URL;

const Community = () => {
  const [activePage, setActivePage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [fetchedPosts, setFetchedPosts] = useState<Post[]>([]);
  const [resultCount, setResultCount] = useState(0);
  const router = useRouter();
  const [isLoading, setIsLoading] = useState(true);
  interface QueryParams {
    [key: string]: string | string[];
  }

  const { id } = router.query;
  const searchType = Array.isArray(id) ? id[0] : id;
  const searchTerm = router.query[searchType] as string;

  const escapeMap = {
    '&lt;': '<',
    '&#12296;': '<',
    '&gt;': '>',
    '&#12297;': '>',
    '&amp;': '&',
    '&quot;': '"',
    '&#x27;': "'"
  };
  const pattern = /&(lt|gt|amp|quot|#x27|#12296|#12297);/g;
  const convertToHtml = (text) =>
    text.replace(pattern, (match, entity) => escapeMap[`&${entity};`] || match);

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        const response = await axios.get(
          `${URL}/api/v1/board/search/${searchType}?${searchType}=${searchTerm}&page=${activePage}`
        );
        setFetchedPosts(response.data.boardResponses);
        setTotalPages(Math.ceil(response.data.count / 10));
        setResultCount(response.data.count);
        setIsLoading(false);
      } catch (error) {
        console.error(error);
        setIsLoading(false);
      }
    };

    fetchData();
  }, [searchType, searchTerm, activePage]);

  const HealthPostView = (post: Post) => () => {
    let categoryname: string;
    switch (post.categoryId) {
      case 1:
        categoryname = 'health';
        break;
      case 2:
        categoryname = 'crossfit';
        break;
      case 4:
        categoryname = 'pilates';
        break;
      case 5:
        categoryname = 'oww';
        break;
      case 6:
        categoryname = 'diet';
        break;
      default:
        categoryname = null;
    }

    axios
      .post(`${URL}/api/v1/board/view/${post.boardId}`)
      .then(() => router.push(`/community/${categoryname}/${post.boardId}`))

      .catch((err) => alert(err));
  };
  const handlePageChange = (
    event: React.MouseEvent<HTMLAnchorElement>,
    data: PaginationProps
  ) => {
    setActivePage(data.activePage as number);
  };
  const handleGoHome = () => {
    router.push('/community');
  };

  return (
    <>
      <Layout>
        <div className={style.Container}>
          <div className={style.Searchbar}>
            <Searchbar />
          </div>
          <div className={style.text}>
            <div>검색 결과: {resultCount}개 </div>
            <div className={style.backToHome} onClick={handleGoHome}>
              <img
                src={'../../../assets/Community/Community.svg'}
                className={style.backHome}
              />
              Home으로 돌아가기
            </div>
          </div>
          <div className={style.result}>
            <div className={style.P_content}>
              {Array.isArray(fetchedPosts) && fetchedPosts.length > 0 ? (
                <ul className={style.allContent}>
                  {fetchedPosts.map((post, index) => (
                    <li
                      className={style.P_list}
                      key={post.boardId}
                      onClick={HealthPostView(post)}
                    >
                      <div>{index + 1}.</div>
                      <div className={style.PostContent}>{post.title}</div>
                      <div
                        className={style.PostContent}
                        dangerouslySetInnerHTML={{
                          __html: convertToHtml(post?.text)
                        }}
                      />
                      <div className={style.nickname}>{post.userNickname}</div>
                    </li>
                  ))}
                </ul>
              ) : (
                <div className={style.noneMsg}> 검색결과가 없습니다. </div>
              )}
            </div>
            <img
              src={'../../../assets/Community/SearchPage.png'}
              className={style.SearchPicture}
            />
          </div>
          <div className={style.PagenationDetail}>
            <Pagination
              activePage={activePage}
              onPageChange={handlePageChange}
              firstItem={null}
              lastItem={null}
              pointing
              secondary
              totalPages={totalPages}
            />
          </div>
        </div>
      </Layout>
    </>
  );
};

export default Community;
