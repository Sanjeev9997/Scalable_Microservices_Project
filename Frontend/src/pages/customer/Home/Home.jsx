
import { Link } from "react-router-dom";
import ProductCard from "../../../components/ProductCard/ProductCard";
import "./Home.css";
import { useEffect, useState } from "react";
import { getAllProducts } from "../../../services/productService";
import { getInventory } from "../../../services/InventoryService";


function Home() {
  const [featuredProducts, setFeaturedProducts] = useState([]);
 

  useEffect(() => {
    const fetchFeaturedProducts = async () => {
      try {
        const response = await getAllProducts();  
        const prods= await Promise.all(
          response.map(async (product) => {
            const stock = getInventory(product.id);
            return {
              id: product.id,
              name: product.name,
              price: product.price,
              oldPrice: 4499,
              category: product.category,
              rating: 4.6,
              stock: stock,
              image: null
            };
          })
        );
        setFeaturedProducts(prods);
      }
      catch (error) {
        console.error("Error fetching featured products:", error);
      } 
    };

    fetchFeaturedProducts();
  }, []);  

  const categories = [
    {
      name: "Electronics",
      icon: "💻",
      description: "Latest gadgets & devices"
    },
    {
      name: "Fashion",
      icon: "👕",
      description: "Style for every occasion"
    },
    {
      name: "Home",
      icon: "🏠",
      description: "Make your home better"
    },
    {
      name: "Accessories",
      icon: "🎧",
      description: "Complete your setup"
    }
  ];

  const features = [
    {
      icon: "🚚",
      title: "Fast Delivery",
      description: "Get your products delivered quickly"
    },
    {
      icon: "🔒",
      title: "Secure Payments",
      description: "Your transactions are protected"
    },
    {
      icon: "↩️",
      title: "Easy Returns",
      description: "Simple and hassle-free returns"
    },
    {
      icon: "💬",
      title: "24/7 Support",
      description: "We are here whenever you need us"
    }
  ];

  return (
    <div className="home-page">

      {/* HERO */}

      <section className="hero-section">

        <div className="hero-content">

          <span className="hero-badge">
            🔥 New arrivals are here
          </span>

          <h1>
            Everything you need,
            <span> delivered faster.</span>
          </h1>

          <p>
            Shop the latest electronics, fashion, accessories
            and more — all in one place.
          </p>

          <div className="hero-buttons">
            <Link to="/products" className="primary-btn">
              Shop Now →
            </Link>

            <Link to="/products" className="secondary-btn">
              Explore Products
            </Link>
          </div>

          <div className="hero-stats">
            <div>
              <strong>10K+</strong>
              <span>Products</span>
            </div>

            <div>
              <strong>25K+</strong>
              <span>Customers</span>
            </div>

            <div>
              <strong>4.8★</strong>
              <span>Customer Rating</span>
            </div>
          </div>

        </div>

        <div className="hero-visual">

          <div className="hero-circle"></div>

          <div className="hero-product">
            <div className="hero-product-icon">
              🎧
            </div>

            <div className="hero-product-info">
              <strong>Wireless Headphones</strong>
              <span>Starting from ₹3,499</span>
            </div>
          </div>

          <div className="floating-card top-card">
            ⭐ 4.8 Rating
          </div>

          <div className="floating-card bottom-card">
            🚚 Fast Delivery
          </div>

        </div>

      </section>

      {/* CATEGORIES */}

      <section className="categories-section">

        <div className="section-header">
          <div>
            <span className="section-label">
              SHOP BY CATEGORY
            </span>

            <h2>Find what you need</h2>

            <p>
              Explore our most popular shopping categories.
            </p>
          </div>

          <Link to="/products" className="view-all-link">
            View All →
          </Link>
        </div>

        <div className="categories-grid">

          {categories.map((category) => (
            <Link
               to={`/products?category=${encodeURIComponent(category.name)}`}
                className="category-card"
                key={category.name}
            >
              <div className="category-icon">
                {category.icon}
              </div>

              <h3>{category.name}</h3>

              <p>{category.description}</p>

              <span className="category-arrow">
                →
              </span>
            </Link>
          ))}

        </div>

      </section>

      {/* FEATURED PRODUCTS */}

      <section className="featured-section">

        <div className="section-header">

          <div>
            <span className="section-label">
              TRENDING NOW
            </span>

            <h2>Featured Products</h2>

            <p>
              Popular picks our customers love.
            </p>
          </div>

          <Link to="/products" className="view-all-link">
            View All Products →
          </Link>

        </div>

        <div className="featured-grid">

          {featuredProducts.map((product) => (
            <ProductCard
              key={product.id}
              product={product}
            />
          ))}

        </div>

      </section>

      {/* PROMO */}

      <section className="promo-section">

        <div className="promo-content">

          <span className="promo-label">
            LIMITED TIME OFFER
          </span>

          <h2>
            Upgrade your setup
            <br />
            without breaking the bank.
          </h2>

          <p>
            Discover amazing deals on electronics and
            accessories.
          </p>

          <Link to="/products" className="promo-btn">
            Explore Deals →
          </Link>

        </div>

        <div className="promo-visual">
          🛍️
        </div>

      </section>

      {/* FEATURES */}

      <section className="features-section">

        <div className="features-grid">

          {features.map((feature) => (
            <div className="feature-card" key={feature.title}>

              <div className="feature-icon">
                {feature.icon}
              </div>

              <div>
                <h3>{feature.title}</h3>
                <p>{feature.description}</p>
              </div>

            </div>
          ))}

        </div>

      </section>

      {/* CTA */}

      <section className="home-cta">

        <div>

          <span>READY TO SHOP?</span>

          <h2>
            Your next favorite product
            is waiting for you.
          </h2>

          <p>
            Browse thousands of products and find
            exactly what you're looking for.
          </p>

        </div>

        <Link to="/products" className="cta-btn">
          Start Shopping →
        </Link>

      </section>

    </div>
  );
}

export default Home;
