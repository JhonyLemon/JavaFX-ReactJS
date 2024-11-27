import { defineConfig } from "vite"
import vue from "@vitejs/plugin-vue"
import { viteSingleFile } from "vite-plugin-singlefile"
import react from '@vitejs/plugin-react'

export default defineConfig({
  plugins: [react(), vue(), viteSingleFile()],
})





