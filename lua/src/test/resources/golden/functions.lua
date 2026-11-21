local count = 0

local function bump(n)
    count = count + n
    return count
end

function Global(ai, goal)
    ai:Do(goal:GetParam(0), bump(1))
end

function T.f(p)
end

function T:m(q)
    self.q = q
end

Register(function(x)
    return x
end)
