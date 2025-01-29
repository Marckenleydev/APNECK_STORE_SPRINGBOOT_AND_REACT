import { useEffect, useState } from "react";

const useCart = () => {
  const getInitialCart = () => {
    const savedCart = localStorage.getItem("cartItems");
    return savedCart ? JSON.parse(savedCart) : [];
  };

  const [cartItems, setCartItems] = useState(getInitialCart());

  useEffect(() => {
    localStorage.setItem("cartItems", JSON.stringify(cartItems));
  }, [cartItems]);

  const addToCart = (product) => {
    setCartItems((prev) => {
      const existingItemIndex = prev.findIndex((item) => item.id === product.id);
      if (existingItemIndex >= 0) {
        const updatedCart = [...prev];
        updatedCart[existingItemIndex].quantity += 1;
        return updatedCart;
      } else {
        return [...prev, { ...product, quantity: 1 }];
      }
    });
  };

  const removeToCart = (productId) => {
    setCartItems((prev) => {
      const existingItemIndex = prev.findIndex((item) => item.id === productId);
      if (existingItemIndex >= 0) {
        const updatedCart = [...prev];
        if (updatedCart[existingItemIndex].quantity > 1) {
          updatedCart[existingItemIndex].quantity -= 1;
        } else {
          updatedCart.splice(existingItemIndex, 1);
        }
        return updatedCart;
      }
      return prev;
    });
  };

  const updateCartItemCount = (value, productId) => {
    setCartItems((prev) => {
      const existingItemIndex = prev.findIndex((item) => item.id === productId);
      if (existingItemIndex >= 0) {
        const updatedCart = [...prev];
        if (value > 0) {
          updatedCart[existingItemIndex].quantity = value;
        } else {
          updatedCart.splice(existingItemIndex, 1);
        }
        return updatedCart;
      } else if (value > 0) {
        return [...prev, { id: productId, quantity: value }];
      }
      return prev;
    });
  };

  return {
    cartItems,
    addToCart,
    removeToCart,
    updateCartItemCount,
  };
};

export default useCart;
