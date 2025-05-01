import axios from 'axios';
import { getToken, setToken } from './tokenService';

const API_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api/v1';

const axiosService = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


//  PRODUCTS

export async function getProductsPage(page) {
  try {
    const response = await axiosService.get(`/products?page=${page}`);
    return response.data;
  } catch (err) {
    console.log(err);
    throw err
  }
}

export async function getProductById(id) {
  try {
    const response = await axiosService.get(`/products/${id}`);
    return response.data;
  } catch (err) {
    console.log(err);
    throw err

  }
}


export async function searchProducts(text, page) {
  try {
    const response = await axiosService.get(`/search?query=${text}&page=${page}`);
    return response.data;
  } catch (err) {
    console.log(err);
    throw err
  }
}


export async function likeProduct(productId) {
  const token = await getToken();
  try {
    const res = await axiosService.put(`/products/${productId}/like`, {}, { headers: { 'Authorization': token } });
    return res.data;
  } catch (err) {
    console.log(err);
  }
}

export async function getRelated(productId) {
  try {
    const res = await axiosService.get(`/products/${productId}/related`);
    return res.data;
  } catch (err) {
    console.log(err);
  }
}

export async function addQuestionToProduct(productId, questionBody) {
  const token = await getToken();
  try {
    const res = await axiosService.post(`/products/${productId}/question`, questionBody, { headers: { 'Authorization': token } });
    return res.data;
  } catch (err) {
    console.log(err);
  }
}

export async function addResponseToProduct(productId, questionId, questionBody) {
  const token = await getToken();
  try {
    const res = await axiosService.put(`/products/${productId}/question/${questionId}`, questionBody, { headers: { 'Authorization': token } });
    return res.data;
  } catch (err) {
    console.log(err);
  }
}


//  CATEGORIES  

export async function getAllCategories() {
  try {
    const response = await axiosService.get(`/categories`);
    return response.data;
  } catch (err) {
    console.log(err);
    throw err
  }
}

export async function getCategoryByID(id, page) {
  const response = await axiosService.get(`/categories/${id}?page=${page}`);
  console.log(response.data);
  return response.data;
}

//  USER


export async function authLogin(email, password) {
  try {
    const response = await axiosService.post('/login', { 'email': email, 'password': password });
    setToken(response.headers.authorization);
    //BORRAR
    console.log(response.data);
    return response.data;
  } catch (err) {
    console.log(err);
    throw err
  }
};


export async function userRegister(registerBody) {
  //BORRAR
  console.log(registerBody);
  try {
    const response = await axiosService.post('/register', registerBody);
    setToken(response.headers.authorization);
    console.log(response.data);
    return response.data;
  } catch (err) {
    console.error(err);
    throw err
  }
};


export async function getUser() {
  const token = await getToken();
  if (token === null) return null;
  try {
    const response = await axiosService.get('/user', { headers: { 'Authorization': token } });
    return response.data;
  } catch (err) {
    throw err;
  }
};

export async function getUserX(id) {
  try {
    const response = await axiosService.get(`/user/${id}`);
    return response.data;
  } catch (err) {
    console.log(`Error: ${err}`);
  }
};


//  CART

export async function getCart() {
  const token = await getToken();
  try {
    const response = await axiosService.get('/cart', { headers: { 'Authorization': token } });
    return response.data;
  } catch (err) {
    console.log(err);
    throw err
  }
}

export async function addToCart(productId, amount) {
  const token = await getToken();
  try {
    const response = await axiosService.put('/cart', { productId, amount }, { headers: { 'Authorization': token } });
    return response.data;
  } catch (err) {
    throw err
  }
}

export async function removeFromCart(productId) {
  const token = await getToken();
  try {
    const response = await axiosService.delete(`/cart/${productId}`, { headers: { 'Authorization': token } });
    return response.data;
  } catch (err) {
    console.log(err);
    throw err
  }
}

export async function purchase(paymentBody) {
  const token = await getToken();
  try {
    const response = await axiosService.post(`/purchase`, paymentBody, { headers: { 'Authorization': token } });
    return response.data;
  } catch (err) {
    console.log(err);
    throw err;
  }
}