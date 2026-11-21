function Act(ai, a, b, c, d)
    if a < 3 and (b or c) then
        F(a)
    elseif d == "s" then
        F(b)
    else
        F(c)
    end
    if ai:GetDist() > 5 then
        F()
    end
    if not (a < b) then
        F()
    end
    if a == 1 or a == 2 then
    end
    if a then
        F()
    else
    end
    if a then
        if b then
            F()
        else
            G()
        end
    else
        H()
    end
end
