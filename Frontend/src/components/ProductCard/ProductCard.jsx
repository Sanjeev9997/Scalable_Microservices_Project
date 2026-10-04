import { Link } from "react-router-dom";
import "./ProductCard.css";
import { addToCart } from "../../services/cartService";

function ProductCard({ product, onAddToCart }) {

  const {
    id,
    name,
    price,
    oldPrice,
    category,
    rating,
    stock,
    image
  } = product;

  const discount =
    oldPrice && oldPrice > price
      ? Math.round(((oldPrice - price) / oldPrice) * 100)
      : 0;

  const isOutOfStock = stock <= 0;


  const handleAddToCart = async () => {

    if (isOutOfStock) return;

    try {

      // Add 1 quantity of the product
      console.log("Adding product to cart:", product.id);
      await addToCart(product.id, 1);
      console.log

      console.log("Product added to cart:", product.id);

      // Optional callback
      if (onAddToCart) {
        onAddToCart(product);
      }

    } catch (error) {

      console.error("Error adding product to cart:", error);

    }
  };


  return (
    <div className="product-card">

      {/* Wishlist */}
      <button className="wishlist-btn">
        ♡
      </button>


      {/* Discount */}
      {discount > 0 && (
        <span className="discount-badge">
          {discount}% OFF
        </span>
      )}


      {/* Product Image */}
      <Link
        to={`/products/${id}`}
        className="product-image"
      >
        {image ? (
          <img
            src={image}
            alt={name}
          />
        ) : (
          <span className="product-placeholder">
            📦
          </span>
        )}
      </Link>


      {/* Product Information */}
      <div className="product-info">

        <span className="product-category">
          {category}
        </span>


        <Link
          to={`/products/${id}`}
          className="product-name"
        >
          {name}
        </Link>


        {/* Rating */}
        <div className="product-rating">
          <span>⭐</span>
          <strong>{rating || "4.5"}</strong>
        </div>


        {/* Price */}
        <div className="product-price">

          <strong>
            ₹{Number(price).toLocaleString("en-IN")}
          </strong>

          {oldPrice && oldPrice > price && (
            <del>
              ₹{Number(oldPrice).toLocaleString("en-IN")}
            </del>
          )}

        </div>


        {/* Stock */}
        <div className="stock-status">

          {isOutOfStock ? (
            <span className="out-stock">
              🔴 Out of Stock
            </span>
          ) : stock <= 10 ? (
            <span className="low-stock">
              🟡 Only {stock} left
            </span>
          ) : (
            <span className="in-stock">
              🟢 In Stock
            </span>
          )}

        </div>


        {/* Add to Cart */}
        <button
          className="add-cart-btn"
          onClick={handleAddToCart}
          disabled={isOutOfStock}
        >
          {isOutOfStock
            ? "Out of Stock"
            : "🛒 Add to Cart"}
        </button>

      </div>

    </div>
  );
}

export default ProductCard;