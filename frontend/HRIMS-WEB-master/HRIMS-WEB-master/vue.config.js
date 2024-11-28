const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    port:7000,
    proxy:{
      '/api':{
        target:'http://localhost:8080',//产生跨域的地址
        changeOrigin:true
      }
    }
  }
})
