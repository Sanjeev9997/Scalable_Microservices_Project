import { useParams, Link } from "react-router-dom";
import { useEffect, useState } from "react";

import { getProductById } from "../../../services/productService";
import { getInventory } from "../../../services/InventoryService";

import "./ProductDetails.css";

function ProductDetails() {

  const { id } = useParams();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {

    const fetchProductDetails = async () => {

      try {

        setLoading(true);
        setError(null);

        // 1. Fetch product by ID
        const productResponse = await getProductById(id);

        // 2. Fetch inventory by product ID
        const inventoryResponse = await getInventory(id);
        // console.log("Inventory Response:", inventoryResponse);
        // console.log("Product Response:", productResponse);
        // 3. Get actual data
        const fetchedProduct = productResponse;
        const inventoryData = inventoryResponse;
        console.log("Fetched Product:", fetchedProduct);
        console.log("Fetched Inventory:", inventoryData);
        // 4. Combine product + stock
        setProduct({
          ...fetchedProduct,
          stock: inventoryData.quantity
        });

      } catch (error) {

        console.error("Error fetching product details:", error);

        setError("Failed to load product details.");

      } finally {

        setLoading(false);

      }

    };

    fetchProductDetails();

  }, [id]);


  // Loading
  if (loading) {
    return <div>Loading...</div>;
  }


  // Error
  if (error) {
    return <div>{error}</div>;
  }


  // Product not found
  if (!product) {
    return <div>Product not found.</div>;
  }


  return (
    <div className="details-page">

      {/* Back */}
      <Link to="/products" className="back-link">
        ← Back to Products
      </Link>


      <div className="details-container">

        {/* Product Image */}
        <div className="details-image">
          {product.image || "📦"}
        </div>


        {/* Product Information */}
        <div className="details-info">

          {/* Category */}
          <span className="details-category">
            {product.category}
          </span>


          {/* Name */}
          <h1>{product.name}</h1>


          {/* Rating */}
          <div className="details-rating">
            ⭐ {product.rating || 4.6}
          </div>


          {/* Price */}
          <div className="details-price">

            ₹{Number(product.price).toLocaleString("en-IN")}

            {product.oldPrice && product.oldPrice > product.price && (
              <del>
                ₹{Number(product.oldPrice).toLocaleString("en-IN")}
              </del>
            )}

          </div>


          {/* Description */}
          <p className="details-description">
            {product.description || "No description available."}
          </p>


          {/* Stock */}
          <div className="stock">

            {product.stock > 0
              ? `🟢 Only ${product.stock} items left`
              : "🔴 Out of Stock"
            }

          </div>


          {/* Quantity */}
          <div className="quantity">

            <button>-</button>

            <span>1</span>

            <button>+</button>

          </div>


          {/* Buttons */}
          <div className="details-buttons">

            <button
              className="cart-btn"
              disabled={product.stock <= 0}
            >
              Add to Cart
            </button>

            <button
              className="buy-btn"
              disabled={product.stock <= 0}
            >
              Buy Now
            </button>

          </div>

        </div>

      </div>

    </div>
  );
}

export default ProductDetails;