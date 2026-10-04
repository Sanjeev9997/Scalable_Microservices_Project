import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

import { getAllProducts } from "../../../services/productService";
import ProductCard from "../../../components/ProductCard/ProductCard";
import "./Products.css";

function Products() {

  const [products, setProducts] = useState([]);

  const [selectedCategory, setSelectedCategory] = useState([]);

  const [sortBy, setSortBy] = useState("");

  const [searchParams, setSearchParams] = useSearchParams();


  const categories = [
    "Electronics",
    "Fashion",
    "Accessories",
    "Home"
  ];


  // Read category from URL
  useEffect(() => {

    const categoryFromUrl = searchParams.get("category");

    if (categoryFromUrl) {

      setSelectedCategory([categoryFromUrl]);

    } else {

      setSelectedCategory([]);

    }

  }, [searchParams]);


  // Fetch products
  useEffect(() => {

    const fetchProducts = async () => {

      try {

        const fetchedProducts = await getAllProducts();

        console.log(
          "Fetched Products:",
          fetchedProducts
        );

        const prods = fetchedProducts.map(
          (product) => ({
            id: product.id,
            name: product.name,
            price: product.price,
            category: product.category,
            rating: 4.6,
            stock: 100,
            image: "🎧"
          })
        );

        setProducts(prods);

      } catch (error) {

        console.error(
          "Error fetching products:",
          error
        );

      }

    };

    fetchProducts();

  }, []);


  // Handle category checkbox
  const handleChange = (e, category) => {

    const isChecked = e.target.checked;

    setSelectedCategory((prevCategories) => {

      let updatedCategories;

      if (isChecked) {

        updatedCategories = [
          ...prevCategories,
          category
        ];

      } else {

        updatedCategories =
          prevCategories.filter(
            (c) => c !== category
          );

      }

      // Update URL
      if (updatedCategories.length === 1) {

        setSearchParams({
          category: updatedCategories[0]
        });

      } else {

        setSearchParams({});

      }

      return updatedCategories;

    });

  };


  // Filter products
  const filteredProducts = products.filter(
    (product) => {

      if (selectedCategory.length === 0) {
        return true;
      }

      return selectedCategory.includes(
        product.category
      );

    }
  );


  // Sort products
  const sortedProducts = [
    ...filteredProducts
  ];


  if (sortBy === "priceLow") {

    sortedProducts.sort(
      (a, b) => a.price - b.price
    );

  } else if (sortBy === "priceHigh") {

    sortedProducts.sort(
      (a, b) => b.price - a.price
    );

  } else if (sortBy === "rating") {

    sortedProducts.sort(
      (a, b) => b.rating - a.rating
    );

  }


  // Clear filters
  const clearFilters = () => {

    setSelectedCategory([]);

    setSearchParams({});

  };


  return (
    <div className="products-page">

      <div className="products-header">

        <div>

          <h1>All Products</h1>

          <p>
            Discover products you'll love.
          </p>

        </div>


        <select
          value={sortBy}
          onChange={(e) =>
            setSortBy(e.target.value)
          }
        >

          <option value="">
            Sort by
          </option>

          <option value="priceLow">
            Price: Low to High
          </option>

          <option value="priceHigh">
            Price: High to Low
          </option>

          <option value="rating">
            Rating
          </option>

        </select>

      </div>


      <div className="products-layout">

        {/* Filters */}

        <aside className="filters">

          <h3>Filters</h3>


          {categories.map((category) => (

            <label key={category}>

              <input
                type="checkbox"
                checked={
                  selectedCategory.includes(
                    category
                  )
                }
                onChange={(e) =>
                  handleChange(
                    e,
                    category
                  )
                }
              />

              {category}

            </label>

          ))}


          <button
            type="button"
            onClick={clearFilters}
          >
            Clear Filters
          </button>

        </aside>


        {/* Products */}

        <div className="product-grid">

          {sortedProducts.length > 0 ? (

            sortedProducts.map((product) => (

              <ProductCard
                key={product.id}
                product={product}
              />

            ))

          ) : (

            <p>
              No products found.
            </p>

          )}

        </div>

      </div>

    </div>
  );
}

export default Products;