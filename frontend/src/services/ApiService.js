import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL;
if (!API_URL) {
  throw new Error('VITE_API_URL is not defined. Please set it in your .env file.');
}


const axiosService = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


//  USER AUTHENTICATION

export async function register(firstName, lastName, email, password) {
  try {
    const response = await axiosService.post('/auth/register', {
      firstName,
      lastName,
      email,
      password,
    });
    return response;
  }
  catch (error) {
    throw new Error('Error during registration: ' + error.response.data);
  }
}

export async function login(email, password) {
  try {
    const response = await axiosService.post('/auth/login', {
      email,
      password,
    });
    return response;
  }
  catch (error) {
    throw new Error('Error during login: ' + error.response.data);
  }
}

//  USER PROFILE

export async function getUserProfile(token) {
  try {
    const response = await axiosService.get(`/users/me`, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error fetching user profile:', error);
    return null;
  }
}

export async function getUserPurchases(token) {
  try {
    const response = await axiosService.get(`/users/me/purchases`, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error fetching user purchases:', error);
    return null;
  }
}

export async function getUserFavorites(token) {
  try {
    const response = await axiosService.get(`/users/me/favorites`, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error fetching user favorites:', error);
    return null;
  }
}

export async function postNewPurchase(token, items) {
  try {
    const response = await axiosService.post(`/users/me/purchases`, {items}, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error posting new purchase:', error);
    throw new Error(error.response.data);
  }
}

export async function toggleFavorite(token, productId) {
  try {
    const response = await axiosService.put(`/users/me/favorites`, { productId }, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error(error);
    throw new Error('Error toggling favorite:', error.response.data);
  }
}

export async function postComment(token, productId, comment) {
  try {
    const response = await axiosService.post(`/products/${productId}/comments`, { comment }, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error posting new comment:', error);
    return null;
  }
}

export async function postReview(token, productId, rating, review) {
  try {
    const response = await axiosService.post(`/products/${productId}/reviews`, { rating, review }, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    throw new Error('Error posting new review:', error.response.data);
  }
}

export async function userBoughtProduct(token, productId) {
  try {
    const response = await axiosService.get(`/users/me/purchases/${productId}`, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error(error.response.data);
    return false;
  }
}


//  PRODUCTS

export async function getProductById(productId) {
  try {
    const response = await axiosService.get(`/products/${productId}`);
    return response.data;
  }
  catch (error) {
    console.error(error);
    return null;
  }
}

export async function searchProducts(q, offset = 0, limit = 10) {
  try {
    const response = await axiosService.get(`/products/search`, {
      params: { q, offset, limit },
    });
    return response.data;
  } catch (error) {
    console.error(error);
    throw new Error('Error fetching products:', error.response.data);
  }
}

export async function getProductsComments(productId) {
  try {
    const response = await axiosService.get(`/products/${productId}/comments`);
    return response.data;
  }
  catch (error) {
    console.error('Error fetching product comments:', error);
    return null;
  }
}

export async function getProductsReviews(productId) {
  try {
    const response = await axiosService.get(`/products/${productId}/reviews`);
    return response.data;
  }
  catch (error) {
    console.error('Error fetching product reviews:', error);
    return null;
  }
}


