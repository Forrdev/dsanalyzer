function Count(n)
    local total = 0
    for i = 1, n do
        total = total + i
    end
    for j = 10, 1, -1 do
        if j < 3 then
            break
        end
        F(j)
    end
    return total
end
