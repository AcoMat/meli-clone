import axios from 'axios';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/';

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
    console.error('Error during registration:', error);
    return null;
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
    console.error('Error during login:', error);
    return null;
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

export async function postNewPurchase(token, purchase) {
  try {
    const response = await axiosService.post(`/users/me/purchases`, purchase, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error posting new purchase:', error);
    return null;
  }
}

export async function toggleFavorite(token, productId) {
  try {
    const response = await axiosService.put(`/users/me/favorites`, {productId}, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error posting new favorite:', error);
    return null;
  }
}

export async function postComment(token, productId, comment) {
  try {
    const response = await axiosService.post(`/products/${productId}/comments`, {comment}, {
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
    const response = await axiosService.post(`/products/${productId}/reviews`, {rating, review}, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error posting new review:', error);
    return null;
  }
}


//  PRODUCTS

export async function getProductById(productId) {
  try {
    const response = await axiosService.get(`/products/${productId}`);
    return response.data;
  }
  catch (error) {
    console.error('Error fetching product by ID:', error);
    return null;
  }
}

export async function searchProducts(query) {
  try {
    const response = await axiosService.get(`/products/search`, {
      params: { query },
    });
    return response.data;
  }
  catch (error) {
    console.error('Error searching products:', error);
    return null;
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


