import React from 'react'
import { Button } from "@/components/ui/button"
import {
  Empty,
  EmptyContent,
  EmptyDescription,
  EmptyHeader,
  EmptyMedia,
  EmptyTitle,
} from "@/components/ui/empty"
import { Annoyed, CornerDownLeft } from "lucide-react"
import { Link } from 'react-router-dom'

function NotFound() {
  return (
    <div>
      <Empty>
        <EmptyHeader>
          <EmptyMedia variant="icon">
            <Annoyed />
          </EmptyMedia>
          <EmptyTitle>Page not found</EmptyTitle>
          <EmptyDescription>
            The page you’re looking for doesn’t exist or may have been moved. Use one of the options below to continue.
          </EmptyDescription>
        </EmptyHeader>
        <EmptyContent className="flex-row justify-center gap-2">
          <Button asChild>
            <Link to="/"><CornerDownLeft /> Back home</Link>
          </Button>
          <Button variant="outline">
            <Link to="/contact">Contact us</Link>
          </Button>
        </EmptyContent>
      </Empty>
    </div>
  )
}

export default NotFound