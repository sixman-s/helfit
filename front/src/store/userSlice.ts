import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export interface User {
  id: string;
  nickname: string;
  birth: string;
}

interface UserState {
  user: User | null;
  status: 'idle' | 'loading' | 'failed';
  error: string | null;
}

const initialState: UserState = {
  user: null,
  status: 'idle',
  error: null
};

export const fetchUser = createAsyncThunk(
  'user/fetchUser',
  async (accessToken: string) => {
    const response = await axios.get(
      `http://ec2-3-34-96-242.ap-northeast-2.compute.amazonaws.com/api/v1/users/}`,
      {
        headers: {
          accessToken: `Bearer ${accessToken}`
        }
      }
    );
    return response.data.body;
  }
);

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchUser.pending, (state) => {
        state.status = 'loading';
      })
      .addCase(fetchUser.fulfilled, (state, action) => {
        state.status = 'idle';
        state.user = action.payload;
      })
      .addCase(fetchUser.rejected, (state, action) => {
        state.status = 'failed';
        state.error = action.error.message;
      });
  }
});

export default userSlice.reducer;
